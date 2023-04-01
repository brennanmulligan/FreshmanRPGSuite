import 'package:flutter_barcode_scanner/flutter_barcode_scanner.dart';

class BarcodeScanner{
  Future<String> scan() async{
    return FlutterBarcodeScanner.scanBarcode(
        0xFF0000.toString(),
        "Cancel",
        true, // true = flash on
        ScanMode.QR);
  }
}