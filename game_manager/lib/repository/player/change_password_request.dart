import 'package:equatable/equatable.dart';

import '../../type_definitions.dart';

///
/// Object to request changing the password for a player.
///
class ChangePasswordRequest extends Equatable {
  final String userName;
  final String newPassword;

  ///
  /// Constructor
  ///
  const ChangePasswordRequest({
    required this.userName,
    required this.newPassword,
  });

  ///
  /// Get properties of object for comparison
  ///
  @override
  List<Object?> get props => [
    userName,
    newPassword,
  ];

  Map<String, dynamic> toJson() {
    return {
      'username': userName,
      'password': newPassword,
    };
  }

  @override
  String toString() {
    return 'ChangePasswordRequest(username: $userName, password: $newPassword)';
  }

  ///
  /// Convert object to JSON.
  ///
  JSON get asJson => {
    'username': userName,
    'password': newPassword,
  };
}
