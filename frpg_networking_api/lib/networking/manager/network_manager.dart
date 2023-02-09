import 'dart:io';

import 'package:flutter/foundation.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:hooks_riverpod/hooks_riverpod.dart';

import '../location/location.dart';
import '../location/get_current_location.dart';

part 'network_manager.freezed.dart';

@freezed
class NetworkManagerState with _$NetworkManagerState {
  ///
  /// Constructor.
  ///
  const factory NetworkManagerState({
    @Default('localhost:8080') baseURL,
  }) = _NetworkManagerState;

  const NetworkManagerState._();
}

class NetworkManager extends StateNotifier<NetworkManagerState> {
  ///
  /// Constructor.
  ///
  NetworkManager({
    NetworkManagerState? state,
  }) : super(
          state ?? const NetworkManagerState(),
        );



  ///
  /// Set base url for Service client.
  ///
  void setBaseURL() {
    //region Copied from lib/src/foundation/constants.dart
    bool isReleaseMode = dotenv.get('dart.vm.product') == 'true';
    bool isProfileMode = dotenv.get('dart.vm.profile') == 'true';
    debugPrint('$isReleaseMode  and  $isProfileMode');
    bool isDebugMode = !isReleaseMode && !isProfileMode;
    bool isWeb = identical(0, 0.0);
    //endregion

    String baseURL;
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
    debugPrint('--- END base URL Dump ---');

    state = state.copyWith(
      baseURL: baseURL,
    );
  }

  ///
  /// Get the current location of the user.
  ///
  Future<Location> getLocation() async {
    final response = await getCurrentLocation();
    debugPrint('--- BEGIN Location Dump ---');
    final location = response.when(
      data: (data) {
        debugPrint('Response Type: Data');
        return Location(
          longitude: data.longitude,
          latitude: data.latitude,
        );
      },
      failure: (failure) {
        debugPrint('Response Type: Failure');
        return const Location(
          longitude: -999,
          latitude: -999,
        );
      },
    );
    debugPrint('Location: ${location.toString()}');
    debugPrint('--- END Location Dump ---');
    return location;
  }
}
