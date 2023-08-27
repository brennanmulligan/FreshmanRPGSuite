import 'package:equatable/equatable.dart';

class LogoutRequest extends Equatable {
  final num playerID;
  final String authKey;

  ///
  /// Constructor
  ///
  const LogoutRequest({required this.playerID, required this.authKey});

  ///
  /// Get properties for comparison
  ///
  @override
  List<Object?> get props => [playerID];

  ///
  /// Converting object to string
  ///
  @override
  String toString() {
    return 'LogoutRequest(playerID: $playerID)';
  }

  ///
  /// Convert object to JSON.
  ///
  Map<String, dynamic> toJson() {
    return {'authKey': authKey};
  }

}
