import 'dart:io';
import 'dart:ui';

import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:path_provider/path_provider.dart';
import 'package:qr_flutter/qr_flutter.dart';
import 'package:gallery_saver/gallery_saver.dart';
import 'package:file_saver/file_saver.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: GenerateQRPage(),
    );
  }
}

class GenerateQRPage extends StatefulWidget {
  @override
  _GenerateQRPageState createState() => _GenerateQRPageState();
}

class _GenerateQRPageState extends State<GenerateQRPage> {
  TextEditingController questIDReader = TextEditingController();
  TextEditingController objectiveIDReader = TextEditingController();
  TextEditingController latitudeReader = TextEditingController();
  TextEditingController longitudeReader = TextEditingController();
  TextEditingController fileNameReader = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('QR GENERATOR'),
      ),
      body: SingleChildScrollView(
        child: Center(
          child: SafeArea(
            minimum: EdgeInsets.all(10.0), // subject to change
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
                    onPressed: () async {
                      setState(() {});
                    },
                    child: const Text('Show Preview')),
                ElevatedButton(
                    onPressed: () async {
                      // Something like this
                      // Location location = await getlocation();
                      // latitudeReader.text = '${location.latitude}';
                      // longitudeReader.text = '${location.longitde}';
                      latitudeReader.text = 'dffd';
                      longitudeReader.text = 'sdfsd';
                      setState(() {});
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
      color: Color.fromARGB(255, 255, 255, 255),
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
