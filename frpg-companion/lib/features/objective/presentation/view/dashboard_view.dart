import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:frpg_companion/features/login/login.dart';
import 'package:frpg_companion/features/objective/objective.dart';
import 'package:hooks_riverpod/hooks_riverpod.dart';

import '../widget/objective_card.dart';

class DashboardView extends HookConsumerWidget {
  const DashboardView({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final notifier = ref.watch(
      ObjectiveProvider.objectiveController.notifier,
    );
    final provider = ref.watch(
      ObjectiveProvider.objectiveController,
    );

    return Scaffold(
      appBar: AppBar(
        automaticallyImplyLeading: false,
        leading: IconButton(
          tooltip: 'Log out of the app.',
          onPressed: () async {
            if (!kDebugMode) {
              final login = ref.read(LoginProvider.loginController.notifier);
              await login.logOut();
            }
            Navigator.pop(context);
          },
          icon: const Icon(
            Icons.logout,
          ),
        ),
        title: Text(
          '${notifier.playerName}\'s Dashboard',
          style: const TextStyle(
            fontWeight: FontWeight.bold,
          ),
        ),
      ),
      body: RefreshIndicator(
        child: Padding(
          padding: const EdgeInsets.only(top: 12),
          child: ListView(
            children:
                (provider.fetchAllObjectiveResponse!.information.isNotEmpty)
                    ? provider.fetchAllObjectiveResponse!.information
                        .map(
                          (e) => ObjectiveCard(
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
        onRefresh: () async {
          await notifier.fetchAllObjective(playerId: notifier.playerID);
        },
      ),
    );
  }
}

// Padding(
//             padding: const EdgeInsets.all(24.0),
//             child: Column(
//               crossAxisAlignment: CrossAxisAlignment.start,
//               children: [
//                 const Text(
//                   'OBJECTIVE CONTROLLER DATA',
//                   style: TextStyle(
//                     fontWeight: FontWeight.bold,
//                   ),
//                 ),
//                 Text(
//                   'playerID: ${notifier.playerID}',
//                 ),
//                 Text(
//                   'questID: ${notifier.questID}',
//                 ),
//                 Text(
//                   'objectiveID : ${notifier.objectiveID}',
//                 ),
//                 Text(
//                   'showResponse : ${notifier.showResponse}',
//                 ),
//                 Text(
//                   'objectiveResponse: '
//                   '${notifier.objectiveResponse.toString()}',
//                 ),
//               ],
//             ),
//           ),
