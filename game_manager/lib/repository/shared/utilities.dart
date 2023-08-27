import 'package:flutter/cupertino.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'dart:io';

class Utilities {

  static String findBaseURL() {
    //region Copied from lib/src/foundation/constants.dart
    bool isReleaseMode = dotenv.get('dart.vm.product') == 'true';
    bool isProfileMode = dotenv.get('dart.vm.profile') == 'true';
    debugPrint('$isReleaseMode  and  $isProfileMode');
    bool isDebugMode = !isReleaseMode && !isProfileMode;
    bool isWeb = identical(0, 0.0);
    //endregion
    String baseURL = '';

    debugPrint('--- BEGIN base URL Dump change---');
    if (isDebugMode) {
      debugPrint('Mode: debug');
      if (isWeb) {
        baseURL = dotenv.get('API_DEBUG_WEB');
      } else {
        if (Platform.isAndroid) {
          baseURL = dotenv.get('API_DEBUG_ANDROID');
        } else {
          baseURL = dotenv.get('API_DEBUG_IOS');
        }
      }
    } else if (isProfileMode) {
      debugPrint('Mode: profile');
      baseURL = dotenv.get('API_PROFILE');
    } else {
      debugPrint('Mode: release');
      baseURL = dotenv.get('API_RELEASE');
    }
    debugPrint('baseURL: $baseURL');
    return baseURL;
  }

}