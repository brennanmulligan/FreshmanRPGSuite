import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:frpg_companion/features/network/network.dart';
import 'package:hooks_riverpod/hooks_riverpod.dart';

///
/// Manages all network junk.
///
class NetworkProvider {
  ///
  /// Manage the network controller.
  ///
  static final networkController =
      StateNotifierProvider<NetworkController, NetworkState>(
    (ref) {
      return NetworkController();
    },
  );

  ///
  /// The service client.
  ///
  static final serviceClient = Provider<ServiceClientHTTP>(
    (ref) {
      return ServiceClientHTTP(
        baseURL: ref.read(NetworkProvider.networkController).baseURL,
      );
    },
  );
}
