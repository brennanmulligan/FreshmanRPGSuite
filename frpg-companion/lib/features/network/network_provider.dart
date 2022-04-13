import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:frpg_companion/features/network/network.dart';

///
/// Manages all network junk.
///
class NetworkProvider {
  ///
  /// Manage the network controller.
  ///
  static final networkController =
      StateNotifierProvider.autoDispose<NetworkController, NetworkState>(
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
        baseURL: ref.read(NetworkProvider.networkController.notifier).baseURL,
      );
    },
  );
}
