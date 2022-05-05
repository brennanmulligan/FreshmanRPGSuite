import 'package:flutter/material.dart';
import 'package:flutter_barcode_scanner/flutter_barcode_scanner.dart';
import 'package:flutter/foundation.dart';
import 'package:frpg_companion/features/network/network.dart';
import 'package:frpg_networking_api/networking/location/location.dart';
import 'package:hooks_riverpod/hooks_riverpod.dart';

import '../../objective.dart';

class ObjectiveCard extends HookConsumerWidget {
  final ObjectiveInformation info;
  const ObjectiveCard({Key? key, required this.info}) : super(key: key);

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final notifier = ref.watch(
      ObjectiveProvider.objectiveController.notifier,
    );

    // final provider = ref.watch(
    //   ObjectiveProvider.objectiveController,
    // );

    return SafeArea(
      minimum: const EdgeInsets.only(left: 12, right: 12),
      child: Card(
        child: Row(
          mainAxisAlignment: MainAxisAlignment.start,
          children: <Widget>[
            Padding(
              padding: const EdgeInsets.all(8.0),
              child: Text(
                info.description,
                style: const TextStyle(
                  fontSize: 16,
                  fontWeight: FontWeight.w500,
                ),
              ),
            ),
            const Spacer(),
            IconButton(
              tooltip: 'Open the QR code scanner.',
              onPressed: () async {
                final network =
                    ref.watch(NetworkProvider.networkController.notifier);
                Location location = await network.getLocation();

                if (kDebugMode) {
                  await notifier.getDataFromCode(
                    qrData: '03_03_40.0622508_-77.5260629',
                    info: info,
                    currentLat: 40.0622508,
                    currentLong: -77.5260629,
                  );
                } else {
                  await notifier.getDataFromCode(
                    qrData: await FlutterBarcodeScanner.scanBarcode(
                      '#ff0000',
                      'Cancel',
                      true,
                      ScanMode.QR,
                    ),
                    info: info,
                    currentLat: location.latitude,
                    currentLong: location.longitude,
                  );
                }
                if (notifier.objectiveResponse?.responseType ==
                    ObjectiveResponseType.completed) {
                  ScaffoldMessenger.of(context).showSnackBar(
                    SnackBar(
                      content: Padding(
                        padding: const EdgeInsets.all(10.0),
                        child: Row(
                          children: [
                            const Icon(
                              Icons.check,
                              color: Colors.green,
                              size: 40,
                            ),
                            const Spacer(),
                            Flexible(
                              flex: 6,
                              child: Text(
                                notifier.objectiveResponse?.responseType
                                        .description() ??
                                    '',
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
                          children: [
                            const Icon(
                              Icons.error,
                              color: Colors.red,
                              size: 40,
                            ),
                            const Spacer(),
                            Flexible(
                              flex: 6,
                              child: Text(
                                notifier.objectiveResponse?.responseType
                                        .description() ??
                                    '',
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
      ),
    );
  }
}
