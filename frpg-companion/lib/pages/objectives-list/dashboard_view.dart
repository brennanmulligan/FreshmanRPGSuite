import 'package:companion_app/model/location_utilities.dart';
import 'package:companion_app/pages/login/login_page.dart';
import 'package:companion_app/pages/objectives-list/bloc/objectives_list_bloc.dart';
import 'package:companion_app/repository/login_repository/login_repository.dart';
import 'package:companion_app/repository/login_repository/logout_request.dart';
import 'package:companion_app/repository/quests_objectives_repository/all-objectives-response.dart';
import 'package:companion_app/repository/shared/general_response.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

import '../../model/barcode_scanner.dart';
import '../../repository/quests_objectives_repository/quests_objectives_repository.dart';
import 'objective_card.dart';

class ObjectivesListView extends StatefulWidget {
  const ObjectivesListView(this.playerId, this.authKey, {Key? key})
      : super(key: key);
  final int playerId;
  final String authKey;

  @override
  State<StatefulWidget> createState() => _ObjectivesListViewState(playerId, authKey);
}

class _ObjectivesListViewState extends State<ObjectivesListView> {
  _ObjectivesListViewState(
    this.playerID,
    this.authKey,
  ) : super();

  final int playerID;
  final String authKey;

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
          leading: IconButton(
            onPressed: () {
            Navigator.of(context).pushReplacement(MaterialPageRoute(
                builder: (context) => ObjectivesListView(playerID, authKey)));
            },
            icon: const Icon(Icons.refresh),
          ),
        ),
        body: RepositoryProvider(
            create: (context) => QuestsObjectivesRepository(),
            child: BlocProvider<ObjectivesListBloc>(
                create: (context) => ObjectivesListBloc(
                    repository: context.read<QuestsObjectivesRepository>(),
                    playerID: playerID,
                    scanner: BarcodeScanner(),
                    geoLocator: GeoLocatorWrapper())..add(RequestObjectivesEvent
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
                      } else if (state is RestfulCompletionRequestInProgress) {
                        return const Center(
                          child: CircularProgressIndicator(),
                        );
                      } else if (state is RestfulCompletionRequestComplete) {
                        return buildObjectiveCompletionScreen(state.response);
                      } else if (state is LocationCheckFailed) {
                        return Center(
                          child: Text(state.errorMsg)
                        );
                      } else if (state is QRCodeCheckFailed) {
                        return const Center(
                          child: Text('Invalid QR code. '
                              'Are you scanning the right one?')
                        );
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

              LoginRepository().logoutPlayer(
                  LogoutRequest(playerID: playerID, authKey: authKey)
              );
              Navigator.of(context).pushReplacement(MaterialPageRoute(
                  builder: (context) => const LoginPage()));
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
