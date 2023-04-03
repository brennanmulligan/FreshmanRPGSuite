import 'package:flutter/cupertino.dart';
import 'package:flutter_barcode_scanner/flutter_barcode_scanner.dart';
import 'package:permission_handler/permission_handler.dart';

class BarcodeScanner {
  Future<String> scan() async {
    // make sure we have camera permission
    var status = await Permission.camera.status;
    if (!status.isGranted) {
      //ask for permission to use the camera
      await Permission.camera.request();
      status = await Permission.camera.status;
    }
    if (status.isGranted) {
      debugPrint("We have permission to use the camera");

      return FlutterBarcodeScanner.scanBarcode(
          "#FF0000",
          "Cancel",
          true, // true = flash on
          ScanMode.QR);
    }

    debugPrint("We don't have permission to use the camera");
    return "Permission Denied";
  }
}
