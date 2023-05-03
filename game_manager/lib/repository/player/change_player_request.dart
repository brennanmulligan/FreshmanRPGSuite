import 'package:equatable/equatable.dart';

import '../../type_definitions.dart';

///
/// Object to request changing the password for a player.
///
class ChangePlayerRequest extends Equatable {
  final String playerName;
  final String password;

  ///
  /// Constructor
  ///
  const ChangePlayerRequest({
    required this.playerName,
    required this.password,
  });

  ///
  /// Get properties of object for comparison
  ///
  @override
  List<Object?> get props => [
    playerName,
    password,
  ];

  Map<String, dynamic> toJson() {
    return {
      'playerName': playerName,
      'password': password,
    };
  }

  @override
  String toString() {
    return 'ChangePasswordRequest(playerName: $playerName, password: $password)';
  }

  ///
  /// Convert object to JSON.
  ///
  JSON get asJson => {
    'playerName': playerName,
    'password': password,
  };
}
