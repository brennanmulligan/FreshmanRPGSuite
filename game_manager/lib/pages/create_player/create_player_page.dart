import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:game_manager/pages/create_player/bloc/create_player_bloc.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:game_manager/pages/get_majors_and_crews/bloc/get_majors_and_crews_bloc.dart';

import '../../repository/player/all_crews_response.dart';
import '../../repository/player/all_majors_response.dart';
import '../../repository/player/crew.dart';
import '../../repository/player/crews_repository.dart';
import '../../repository/player/major.dart';
import '../../repository/player/majors_repository.dart';
import '../../repository/player/player_repository.dart';
import '../../repository/player/basic_response.dart';

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

  @override
  Widget build(BuildContext context) {
    return MultiRepositoryProvider(
        providers: [
          RepositoryProvider(create: (context) => PlayerRepository(dio: Dio())),
          RepositoryProvider(create: (context) => MajorsRepository(dio: Dio())),
          RepositoryProvider(create: (context) => CrewsRepository(dio: Dio())),
        ],
        child: MultiBlocProvider(
            providers: [
              BlocProvider<CreatePlayerBloc>(
                  create: (context) => CreatePlayerBloc(
                      playerRepository: context.read<PlayerRepository>())),
              BlocProvider<GetMajorsAndCrewsBloc>(
                  create: (context) => GetMajorsAndCrewsBloc(
                        MajorsRepository: context.read<MajorsRepository>(),
                        CrewsRepository: context.read<CrewsRepository>(),
                      )..add(SendCreateMajorsCrewEvent(0, 0))) //TODO: refactor this to not take in arguments)
            ],
            child: Scaffold(
                resizeToAvoidBottomInset: false,
                appBar: AppBar(
                  title: const Text('Create Player'),
                ),
                body: Column(
                  children: [
                    BlocConsumer<CreatePlayerBloc, CreatePlayerState>(
                        listener: (context, state) {},
                        builder: (context, state) {
                          if (state is CreatePlayerComplete) {
                            return buildRequestCompleteScreen(state.response);
                          } else {
                              return BlocBuilder<
                                GetMajorsAndCrewsBloc,
                                GetMajorsAndCrewsState>(
                                  builder: (context, state) {
                                    if (state is GetMajorCrewComplete) {
                                      return buildInputScreen(state.crewResponse, state.majorResponse);
                                    } else {
                                      return const Center (
                                        child: CircularProgressIndicator()
                                      );
                                    }
                                  }
                                );
                          }
                        }),

                  ]
                )
                )));
  }

  Widget buildLoadScreen() =>
      const Padding(
          padding: EdgeInsets.all(24.0),
          child: Scaffold(
            body: Center(
              child: CircularProgressIndicator(),
            ),
          )
      );

  Widget buildInputScreen(AllCrewsResponse crewsResponse, AllMajorsResponse majorsResponse) => Padding(
        padding: const EdgeInsets.all(24.0),
        child: Center(
          child: Column(
            children: [
              TextField(
                controller: username,
                decoration:  const InputDecoration(
                  label: Row(
                    children: [
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
                decoration: const InputDecoration(
                  label: Row(
                    children: [
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
                items: crewsResponse.crews.map<DropdownMenuItem<int>>((Crew crew) {
                  return DropdownMenuItem<int>(
                    value: crew.crewID,
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
                items: majorsResponse.majors.map<DropdownMenuItem<int>>((Major major) {
                  return DropdownMenuItem<int>(
                    value: major.majorID,
                    child: Text(major.name),
                  );
                }).toList(),
              ),
              TextField(
                controller: section,
                keyboardType: TextInputType.number,
                decoration:  const InputDecoration(
                  label: Row(
                    children: [
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
                  section: section)
            ],
          ),
        ),
      );

  Widget buildRequestCompleteScreen(BasicResponse data) =>
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
  final int crew;
  final int major;
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
            // TODO make sure this functions, crew & major were parsed as ints??
            .add(SendCreatePlayerEvent(username.text, password.text,
                crew, major, int.parse(section.text))),
        child: const Text(
          "Create Player",
          style: TextStyle(color: Colors.black),
        ),
      ),
    );
  }
}
