import 'dart:io';
import 'dart:ui';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:frpg_networking_api/networking/location/location.dart';
import 'package:path_provider/path_provider.dart';
import 'package:qr_flutter/qr_flutter.dart';
import 'package:gallery_saver/gallery_saver.dart';
import 'package:file_saver/file_saver.dart';
import 'package:hooks_riverpod/hooks_riverpod.dart';
import '../../network/network_provider.dart';

class CreateQRView extends HookConsumerWidget {
  const CreateQRView({Key? key}) : super(key: key);
  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final questIDReader = TextEditingController();
    final objectiveIDReader = TextEditingController();
    final latitudeReader = TextEditingController();
    final longitudeReader = TextEditingController();
    final fileNameReader = TextEditingController();
    return Scaffold(
      appBar: AppBar(
        title: const Text('QR GENERATOR'),
      ),
      body: SingleChildScrollView(
        child: Center(
          child: SafeArea(
            minimum: const EdgeInsets.all(10.0), // subject to change
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                QrImage(
                  data:
                      '${questIDReader.text}_${objectiveIDReader.text}_${latitudeReader.text}_${longitudeReader.text}',
                  size: 300,
                ),
                TextField(
                  controller: questIDReader,
                  decoration: const InputDecoration(labelText: 'Enter questID'),
                ),
                TextField(
                  controller: objectiveIDReader,
                  decoration:
                      const InputDecoration(labelText: 'Enter objectiveID'),
                ),
                TextField(
                  controller: latitudeReader,
                  decoration:
                      const InputDecoration(labelText: 'Enter latitude'),
                ),
                TextField(
                  controller: longitudeReader,
                  decoration:
                      const InputDecoration(labelText: 'Enter longitude'),
                ),
                TextField(
                  controller: fileNameReader,
                  decoration:
                      const InputDecoration(labelText: 'Enter fileName'),
                ),
                ElevatedButton(
                    onPressed: () async {}, child: const Text('Show Preview')),
                ElevatedButton(
                    onPressed: () async {
                      final network =
                          ref.watch(NetworkProvider.networkController.notifier);
                      Location location = await network.getLocation();

                      latitudeReader.text =
                          location.latitude.toStringAsFixed(6);
                      longitudeReader.text =
                          location.longitude.toStringAsFixed(6);
                    },
                    child: const Text('GET CURRENT LOCATION')),
                ElevatedButton(
                    onPressed: () async {
                      createQR(
                          questIDReader.text,
                          objectiveIDReader.text,
                          latitudeReader.text,
                          longitudeReader.text,
                          fileNameReader.text);
                    },
                    child: const Text('GENERATE QR'))
              ],
            ),
          ),
        ),
      ),
    );
  }

  ///
  /// Generates QRcode from given data and save it to an image file
  ///
  Future<void> createQR(String questID, String objectiveID, String latitude,
      String longitude, String fileName) async {
    String message = '${questID}_${objectiveID}_${latitude}_$longitude';
    final qrValidationResult = QrValidator.validate(
      data: message,
      version: QrVersions.auto,
      errorCorrectionLevel: QrErrorCorrectLevel.L,
    );

    final qrCode = qrValidationResult.qrCode ?? QrCode(1, 1);
    final painter = QrPainter.withQr(
      qr: qrCode,
      color: const Color.fromARGB(255, 255, 255, 255),
      gapless: true,
      embeddedImageStyle: null,
      embeddedImage: null,
    );
    final picData =
        await painter.toImageData(2048, format: ImageByteFormat.png);

    if (!kIsWeb) {
      // If you are on mobile, save to phones gallery
      Directory tempDir = await getTemporaryDirectory();
      String tempPath = tempDir.path;
      String path = '$tempPath/$fileName.png';
      await writeToFile(picData!, path);
      GallerySaver.saveImage(path);
    } else {
      // If you are on web, save to downloads folder
      final buffer = picData!.buffer;
      await FileSaver.instance.saveFile(
          '$fileName.jpeg',
          buffer.asUint8List(picData.offsetInBytes, picData.lengthInBytes),
          '.jpeg');
    }
  }

  ///
  /// Save a file to the given path
  ///
  Future<void> writeToFile(ByteData data, String path) async {
    final buffer = data.buffer;
    await File(path).writeAsBytes(
        buffer.asUint8List(data.offsetInBytes, data.lengthInBytes));
  }
}
