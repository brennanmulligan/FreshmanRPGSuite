import 'dart:convert';
import 'dart:typed_data';

import 'package:dio/dio.dart';
import 'package:file_picker/file_picker.dart';
import 'package:flutter/material.dart';
import 'package:game_manager/pages/create_player/bloc/create_player_bloc.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:game_manager/pages/shared/widgets/password.dart';

import '../../repository/player/create_many_players_response.dart';
import '../../repository/player/get_all_crews_response.dart';
import '../../repository/player/get_all_majors_response.dart';
import '../../repository/player/crew.dart';
import '../../repository/player/major.dart';
import '../../repository/player/player_repository.dart';
import '../create_many_players/bloc/create_many_players_bloc.dart';
import '../shared/widgets/notification_card.dart';

class CreatePlayerPage extends StatefulWidget {
  const CreatePlayerPage({Key? key}) : super(key: key);

  @override
  State<CreatePlayerPage> createState() => _CreatePlayerPageState();
}

class _CreatePlayerPageState extends State<CreatePlayerPage> {
  final username = TextEditingController();
  final password = TextEditingController();
  final section = TextEditingController();
  int? crewsValue;
  int? majorsValue;
  PasswordValidator validator = PasswordValidator();

  @override
  void initState() {
    super.initState();
    crewsValue = 0;
    majorsValue = 0;
  }

  @override
  dispose() {
    super.dispose();
    username.dispose();
    password.dispose();
    section.dispose();
  }

  refresh() {
    setState(() {});
  }

  @override
  Widget build(BuildContext context) {
    return MultiRepositoryProvider(
        providers: [
          RepositoryProvider(create: (context) => PlayerRepository(dio: Dio())),
        ],
        child: MultiBlocProvider(
            providers: [
              BlocProvider<CreatePlayerBloc>(
                  create: (context) => CreatePlayerBloc(
                      playerRepository: context.read<PlayerRepository>())
              ..add(SendGetMajorsAndCrewsEvent())),
              BlocProvider<CreateManyPlayersBloc>(
                create: (context) => CreateManyPlayersBloc(
                  playerRepository: context.read<PlayerRepository>())),
            ],
            child: Scaffold(
                resizeToAvoidBottomInset: false,
                appBar: AppBar(
                  title: const Text('Create Player'),
                ),
                body: Column(children: [
                  BlocConsumer<CreatePlayerBloc, CreatePlayerPageState>(
                      listener: (context, state) {
                    if (state is CreatePlayerComplete) {
                      ScaffoldMessenger.of(context).showSnackBar(
                        SnackBar(
                            content: NotificationCard(
                                cardTitle: state.response.success
                                    ? "Success"
                                    : "Error",
                                description: state.response.description,
                                success: state.response.success),
                            duration: const Duration(seconds: 3),
                            behavior: SnackBarBehavior.floating,
                            padding: EdgeInsets.zero),
                      );
                    }
                  }, builder: (context, state) {
                    return BlocBuilder<CreatePlayerBloc,
                        CreatePlayerPageState>(builder: (context, state) {
                      if (state is GetMajorsAndCrewsComplete) {
                        return buildInputScreen(
                            state.crewsResponse, state.majorsResponse);
                      } else {
                        return const Center(child: CircularProgressIndicator());
                      }
                    });
                  }),
                  BlocConsumer<CreateManyPlayersBloc, CreateManyPlayersState>(
                    listener: (context, state){
                      if(state is CreateManyPlayersComplete)
                      {
                        List<CreatePlayerWithNameResponse> failures = state.response.failed;
                        String description = "";
                        bool success = true;
                        if(failures.length > 0)
                        {
                          for (CreatePlayerWithNameResponse f in failures)
                          {
                            description = description + f.playerName + ": " + f.description + "\n";
                          }
                          success = false;
                        }
                        else
                        {
                          description = state.response.description;
                        }
                        ScaffoldMessenger.of(context).showSnackBar(
                            SnackBar(
                              content: NotificationCard(
                                  cardTitle: state.response.success
                                      ? "File Upload Success, players created"
                                      : "File Upload Error.Please try again.",
                                  description: description,
                                  success: success
                              ),
                              duration: const Duration(seconds: 10),
                              behavior: SnackBarBehavior.floating,
                              padding: EdgeInsets.zero,
                            )
                        );
                      }
                    },
                    builder: (context, state){
                      return BlocBuilder<CreateManyPlayersBloc, CreateManyPlayersState>(
                        builder:(context, state){
                          if(state is CreateManyPlayersLoading){
                            return const Center (
                              child: CircularProgressIndicator()
                            );
                          }else {
                            return const Center (
                              child: Icon(
                                Icons.upload_file_outlined,
                                color: Colors.pink,
                              )
                            );
                          }
                        }
                      );
                    }
                  )
                ]))));
  }

  Widget buildLoadScreen() => const Padding(
      padding: EdgeInsets.all(24.0),
      child: Scaffold(
        body: Center(
          child: CircularProgressIndicator(),
        ),
      ));

  Widget buildInputScreen(
          GetAllCrewsResponse crewsResponse, GetAllMajorsResponse majorsResponse) =>
      Padding(
        padding: const EdgeInsets.all(24.0),
        child: Center(
          child: Column(
            children: [
              TextField(
                controller: username,
                decoration: InputDecoration(
                  label: Row(
                    children: const [
                      Icon(Icons.person),
                      SizedBox(
                        width: 10,
                      ),
                      Text('Username'),
                    ],
                  ),
                  fillColor: Colors.grey,
                ),
              ),
              PasswordField(
                controller: password,
                validator: validator,
                notifyParent: refresh,
              ),
              DropdownButtonFormField<int>(
                decoration: const InputDecoration(
                  prefixIcon: Icon(Icons.diversity_3, color: Colors.black),
                ),
                hint: const Text("Crew"),
                value: crewsValue,
                isExpanded: true,
                onChanged: (int? value) {
                  setState(() {
                    crewsValue = value!;
                  });
                },
                items:
                    crewsResponse.crews.map<DropdownMenuItem<int>>((Crew crew) {
                  return DropdownMenuItem<int>(
                    value: crew.id,
                    child: Text(crew.name),
                  );
                }).toList(),
              ),
              DropdownButtonFormField<int>(
                decoration: const InputDecoration(
                  prefixIcon: Icon(Icons.school, color: Colors.black),
                ),
                hint: const Text("Major"),
                value: majorsValue,
                isExpanded: true,
                onChanged: (int? value) {
                  setState(() {
                    majorsValue = value!;
                  });
                },
                items: majorsResponse.majors
                    .map<DropdownMenuItem<int>>((Major major) {
                  return DropdownMenuItem<int>(
                    value: major.id,
                    child: Text(major.name),
                  );
                }).toList(),
              ),
              TextField(
                controller: section,
                keyboardType: TextInputType.number,
                decoration: InputDecoration(
                  label: Row(
                    children: const [
                      Icon(Icons.catching_pokemon),
                      SizedBox(
                        width: 10,
                      ),
                      Text('Section'),
                    ],
                  ),
                  fillColor: Colors.grey,
                ),
              ),
              const SizedBox(
                height: 60,
              ),
              SubmitButtonBuilder(
                  username: username,
                  password: password,
                  crew: crewsValue!,
                  major: majorsValue!,
                  section: section,
                  isValid: validator.isSecure),
              const UploadButton(),
            ],
          ),
        ),
      );
}
class UploadButton extends StatelessWidget {
  const UploadButton(
      {Key? key,}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Padding(
        padding: const EdgeInsets.all(1),
        child: ElevatedButton(
          style: ElevatedButton.styleFrom(
            foregroundColor: Colors.white,
            backgroundColor: Colors.pink,
            minimumSize: const Size(double.infinity, 50),
            shape: const RoundedRectangleBorder(
              borderRadius: BorderRadius.all(Radius.circular(1)),
            ),
          ),
          child: const Text(
              "Upload File"),
          onPressed: () => _pickFile(context),
        )
    );
  }
}

Future<void> _pickFile(BuildContext context) async{

  FilePickerResult? file = await FilePicker.platform.pickFiles(
    type: FileType.custom,
    allowedExtensions: ['csv'],
  );
  if(file != null){

    Uint8List fileUint8List = Uint8List.fromList(file.files.first.bytes as List<int>);
    String fileContent = utf8.decode(fileUint8List);
    List<String> fileLines = fileContent.split('\n');

    BlocProvider.of<CreateManyPlayersBloc>(context).add(SendCreateManyPlayersEvent(fileLines));
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(content: Text('Selected file: ${file.files.single.name}')),
    );
  }

}


class SubmitButtonBuilder extends StatelessWidget {
  SubmitButtonBuilder(
      {Key? key,
      required this.username,
      required this.password,
      required this.crew,
      required this.major,
      required this.section,
      required this.isValid})
      : super(key: key);

  final TextEditingController username;
  final TextEditingController password;
  final int crew;
  final int major;
  final TextEditingController section;
  bool isValid;

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.all(1),
      child: ElevatedButton(
        style: ElevatedButton.styleFrom(
          foregroundColor: Colors.blue,
          minimumSize: const Size(double.infinity, 50),
          shape: const RoundedRectangleBorder(
            borderRadius: BorderRadius.all(Radius.circular(1)),
          ),
        ),
        onPressed: !isValid ? null :() => BlocProvider.of<CreatePlayerBloc>(context)
            .add(SendCreatePlayerEvent(username.text, password.text, crew,
                major, int.parse(section.text))),
        child: const Text(
          "Create Player",
          style: TextStyle(color: Colors.black),
        ),
      ),
    );
  }
}
