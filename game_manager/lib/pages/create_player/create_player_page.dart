import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:game_manager/pages/create_player/bloc/create_player_bloc.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

import '../../repository/player_repository.dart';
import 'models/create_player_response.dart';

class CreatePlayerPage extends StatefulWidget {
  const CreatePlayerPage({Key? key}) : super(key: key);

  @override
  State<CreatePlayerPage> createState() => _CreatePlayerPageState();
}

class _CreatePlayerPageState extends State<CreatePlayerPage> {
  final username = TextEditingController();
  final password = TextEditingController();
  final crew = TextEditingController();
  final major = TextEditingController();
  final section = TextEditingController();

  // need to get the actual data for this at some point
  final List<String> crewsList = <String>['One', 'Two', 'Three'];
  final List <String> majorsList = <String>['The', 'Boating School', 'Is'];

  String? crewsValue;
  String? majorsValue;

  @override
  void initState() {
    super.initState();
  }

  @override
  dispose() {
    super.dispose();
    username.dispose();
    password.dispose();
    crew.dispose();
    major.dispose();
    section.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        resizeToAvoidBottomInset: false,
        appBar: AppBar(
          title: const Text('Create Player'),
        ),
        body:
        RepositoryProvider(
          create: (context) => PlayerRepository(dio: Dio()),

          child: BlocProvider<CreatePlayerBloc>(
            create: (context) => CreatePlayerBloc
              (playerRepository: context.read<PlayerRepository>()),

        child:
          BlocConsumer<CreatePlayerBloc, CreatePlayerState>(
            listener: (context, state) {},
            builder: (context, state) {
              if (state is CreatePlayerComplete) {
                return buildRequestCompleteScreen(state.response);
              } else {
                return buildInputScreen();
              }
            })
    )));
  }

  Widget buildInputScreen() => Padding(
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
              TextField(
                controller: password,
                decoration: InputDecoration(
                  label: Row(
                    children: const [
                      Icon(Icons.key),
                      SizedBox(
                        width: 10,
                      ),
                      Text('Password'),
                    ],
                  ),
                  fillColor: Colors.grey,
                ),
              ),
              DropdownButton(
                hint: Text("Crew"),
                value: crewsValue,
                icon: const Icon(Icons.arrow_downward),
                onChanged: (String? value) {
                  setState(() {
                    crewsValue = value!;
                  });
                },
                items: crewsList.map<DropdownMenuItem<String>>((String value) {
                  return DropdownMenuItem<String>(
                    value: value,
                    child: Text(value),
                  );
                }).toList(),
              ),
              TextField(
                controller: major,
                keyboardType: TextInputType.number,
                decoration: InputDecoration(
                  label: Row(
                    children: const [
                      Icon(Icons.key),
                      SizedBox(
                        width: 10,
                      ),
                      Text('Major'),
                    ],
                  ),
                  fillColor: Colors.grey,
                ),
              ),
              TextField(
                controller: section,
                keyboardType: TextInputType.number,
                decoration: InputDecoration(
                  label: Row(
                    children: const [
                      Icon(Icons.key),
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
              SubmitButtonBuilder( username: username,
                  password: password, crew: crew, major: major, section: section)
            ],
          ),
        ),
      );

  Widget buildRequestCompleteScreen(CreatePlayerResponse data) =>
      Center(child: Text('$data'));
}

class SubmitButtonBuilder extends StatelessWidget {
  const SubmitButtonBuilder(
      {Key? key,
        required this.username,
        required this.password,
        required this.crew,
        required this.major,
        required this.section})
      : super(key: key);

  final TextEditingController username;
  final TextEditingController password;
  final TextEditingController crew;
  final TextEditingController major;
  final TextEditingController section;

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
        onPressed: () => BlocProvider.of<CreatePlayerBloc>(context)
            .add(SendCreatePlayerEvent(username.text, password.text,int.parse(crew
            .text), int.parse(major.text), int.parse(section.text))),
        child: const Text(
          "Create Player",
          style: TextStyle(color: Colors.black),
        ),
      ),
    );
  }
}