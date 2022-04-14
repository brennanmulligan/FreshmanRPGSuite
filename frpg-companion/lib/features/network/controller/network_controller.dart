import 'dart:io';

import 'package:flutter/foundation.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:freezed_annotation/freezed_annotation.dart';
import 'package:frpg_companion/features/network/location/location_service.dart';
import 'package:geolocator/geolocator.dart';

part 'network_controller.freezed.dart';

///
/// State for base URL.
///
@freezed
class NetworkState with _$NetworkState {
  ///
  /// Constructor.
  ///
  const factory NetworkState({
    @Default('') baseURL,
    @Default(-999) latitude,
    @Default(-999) longitude,
  }) = _NetworkState;

  ///
  /// Network state.
  ///
  const NetworkState._();
}

///
///
///
class NetworkController extends StateNotifier<NetworkState> {
  ///
  /// Constructor.
  ///
  NetworkController({
    NetworkState? state,
  }) : super(state ?? const NetworkState());

  ///
  /// Set base URL.
  ///
  void assignBaseURL() {
    String baseURL;

    if (kDebugMode) {
      if (Platform.isAndroid) {
        baseURL = dotenv.get('API_DEBUG_ANDROID');
      } else {
        baseURL = dotenv.get('API_DEBUG_IOS');
      }
    } else if (kProfileMode) {
      baseURL = dotenv.get('API_PROFILE');
    } else {
      baseURL = dotenv.get('API_RELEASE');
    }

    debugPrint(baseURL);

    state = state.copyWith(
      baseURL: baseURL,
    );
  }

  void getLocation() async {
    final location = await LocationService.getPosition();
    location.when(
      data: (data) {
        print('received with ${data.longitude} ${data.latitude}');
        state = state.copyWith(
          longitude: data.longitude,
          latitude: data.latitude,
        );
        print('updated with ${state.longitude} ${state.latitude}');
      },
      failure: (failure) {
        print('received with -999 -999');
        state = state.copyWith(
          longitude: -999,
          latitude: -999,
        );
        print('updated with ${state.longitude} ${state.latitude}');
      },
    );
  }

  ///
  /// Get base URL.
  ///
  String get baseURL => state.baseURL;

  num get longitude => state.longitude;

  num get latitude => state.latitude;
}
