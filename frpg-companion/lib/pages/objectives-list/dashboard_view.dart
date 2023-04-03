import 'package:companion_app/pages/objectives-list/bloc/objectives_list_bloc.dart';
import 'package:companion_app/repository/quests_objectives_repository/all-objectives-response.dart';
import 'package:companion_app/repository/shared/general_response.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

import '../../model/barcode_scanner.dart';
import '../../repository/quests_objectives_repository/quests_objectives_repository.dart';
import 'objective_card.dart';

class ObjectivesListView extends StatefulWidget {
  const ObjectivesListView(this.playerId, {Key? key})
      : super(key: key);
  final int playerId;

  @override
  State<StatefulWidget> createState() => _ObjectivesListViewState(playerId);
}

class _ObjectivesListViewState extends State<ObjectivesListView> {
  _ObjectivesListViewState(
    this.playerID
  ) : super();

  final int playerID;

  @override
  void initState() {
    super.initState();
  }

  @override
  void dispose() {
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        resizeToAvoidBottomInset: false,
        appBar: AppBar(
          title: const Text('Objectives'),
        ),
        body: RepositoryProvider(
            create: (context) => QuestsObjectivesRepository(),
            child: BlocProvider<ObjectivesListBloc>(
                create: (context) => ObjectivesListBloc(
                    repository: context.read<QuestsObjectivesRepository>(),
                    playerID: playerID,
                    scanner: BarcodeScanner())..add(RequestObjectivesEvent
    (playerID)),
                child: BlocConsumer<ObjectivesListBloc, ObjectivesListState>(
                    listener: (context, state) {},
                    builder: (context, state) {
                      if (state is ObjectivesListInitial) {
                        return const Center(
                          child: CircularProgressIndicator(),
                        );
                      } else if (state is ObjectivesListLoading) {
                        return const Center(
                          child: CircularProgressIndicator(),
                        );
                      } else if (state is ObjectiveListLoaded) {
                        return buildObjectivesListScreen(state.response);
                      } else if (state is ObjectiveCompletionInProgress) {
                        return const Center(
                          child: CircularProgressIndicator(),
                        );
                      } else if (state is ObjectiveCompletionComplete) {
                        return buildObjectiveCompletionScreen(state.response);
                      } else {
                        return const Center(
                          child: Text('Error'),
                        );
                      }
                    }))));
  }

  Widget buildObjectivesListScreen(AllObjectivesResponse response) => Scaffold(
        appBar: AppBar(
          automaticallyImplyLeading: false,
          leading: IconButton(
            tooltip: 'Log out of the app.',
            onPressed: () async {
              // if (!kDebugMode) {
              //   final login = ref.read(LoginProvider.loginController.notifier);
              //   await login.logOut();
              // }
              // Navigator.pop(context);
            },
            icon: const Icon(
              Icons.logout,
            ),
          ),
          title: const Text(
            'Pending Objectives',
            style: TextStyle(
              fontWeight: FontWeight.bold,
            ),
          ),
        ),
        body: Padding(
          padding: const EdgeInsets.only(top: 12),
          child: ListView(
            children: (response.objectives.isNotEmpty)
                ? response.objectives
                    .map(
                      (e) => ObjectiveCard(playerID: playerID,
                        info: e,
                      ),
                    )
                    .toList()
                : [
                    const Center(
                      child: Padding(
                        padding: EdgeInsets.all(48.0),
                        child: Text(
                          'No objectives found.',
                          style: TextStyle(
                            fontWeight: FontWeight.bold,
                          ),
                        ),
                      ),
                    ),
                  ],
          ),
        ),
      );
}

Widget buildObjectiveCompletionScreen(GeneralResponse response) => const Center(
      child: Text("Objective Completed"),
    );
