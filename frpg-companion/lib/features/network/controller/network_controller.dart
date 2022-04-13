import 'package:flutter/foundation.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:freezed_annotation/freezed_annotation.dart';

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
      baseURL = dotenv.get('API_DEBUG');
    } else if (kProfileMode) {
      baseURL = dotenv.get('API_PROFILE');
    } else {
      baseURL = dotenv.get('API_RELEASE');
    }

    state = state.copyWith(
      baseURL: baseURL,
    );
  }

  ///
  /// Get base URL.
  ///
  String get baseURL => state.baseURL;
}
