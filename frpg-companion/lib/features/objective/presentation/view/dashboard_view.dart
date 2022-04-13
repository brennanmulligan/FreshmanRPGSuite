import 'package:flutter/material.dart';
import 'package:flutter_barcode_scanner/flutter_barcode_scanner.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:frpg_companion/features/objective/objective.dart';
import 'package:frpg_companion/features/objective/objective_provider.dart';

class DashboardView extends ConsumerWidget {
  final String username;
  final num playerID;

  const DashboardView({
    Key? key,
    required this.username,
    required this.playerID,
  }) : super(key: key);

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final notifier = ref.watch(
      ObjectiveProvider.objectiveController.notifier,
    );

    final provider = ref.watch(
      ObjectiveProvider.objectiveController,
    );

    notifier.setPlayer(playerID, username);

    return Scaffold(
      appBar: AppBar(
        automaticallyImplyLeading: false,
        leading: IconButton(
          tooltip: 'Log out of the app.',
          onPressed: () => Navigator.pop(context),
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
        actions: [
          IconButton(
            tooltip: 'Navigate to the settings page.',
            onPressed: () =>
                debugPrint('This would open a settings page eventually.'),
            icon: const Icon(Icons.settings),
          ),
          IconButton(
            tooltip: 'Open the QR code scanner.',
            onPressed: () async {
              await notifier.getDataFromCode(
                qrData: await FlutterBarcodeScanner.scanBarcode(
                  '#ff0000',
                  'Cancel',
                  true,
                  ScanMode.QR,
                ),
              );
              if(notifier.objectiveResponse?.responseType == ObjectiveResponseType.completed){
                ScaffoldMessenger.of(context).showSnackBar(
                  SnackBar(
                    content: Padding(
                      padding: const EdgeInsets.all(10.0),
                      child: Row(
                        children: const [
                          Icon(
                            Icons.check,
                            color: Colors.green,
                            size: 40,
                          ),
                          Spacer(),
                          Flexible(
                            flex: 6,
                            child: Text(
                              'Objective Complete!',
                            ),
                          ),
                        ],
                      ),
                    ),
                  ),
                );
              } else {
                ScaffoldMessenger.of(context).showSnackBar(
                  SnackBar(
                    content: Padding(
                      padding: const EdgeInsets.all(10.0),
                      child: Row(
                        children: const [
                          Icon(
                            Icons.error,
                            color: Colors.red,
                            size: 40,
                          ),
                          Spacer(),
                          Flexible(
                            flex: 6,
                            child: Text(
                              'Failed to complete Objective.',
                            ),
                          ),
                        ],
                      ),
                    ),
                  ),
                );
              }
            },
            icon: const Icon(
              Icons.qr_code,
            ),
          ),
        ],
      ),
      body: ListView(
        children: [
          Padding(
            padding: const EdgeInsets.all(24.0),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                const Text(
                  'OBJECTIVE CONTROLLER DATA',
                  style: TextStyle(
                    fontWeight: FontWeight.bold,
                  ),
                ),
                Text(
                  'playerID: ${notifier.playerID}',
                ),
                Text(
                  'questID: ${notifier.questID}',
                ),
                Text(
                  'objectiveID : ${notifier.objectiveID}',
                ),
                Text(
                  'showResponse : ${notifier.showResponse}',
                ),
                Text(
                  'objectiveResponse: '
                  '${notifier.objectiveResponse.toString()}',
                )
              ],
            ),
          ),
        ],
      ),
    );
  }
}
