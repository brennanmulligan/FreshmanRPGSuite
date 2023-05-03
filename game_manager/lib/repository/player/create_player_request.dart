import 'package:equatable/equatable.dart';

import '../../type_definitions.dart';

///
/// Object to request creating a player.
///
class CreatePlayerRequest extends Equatable {
  final String playerName;
  final String password;
  final num crew;
  final num major;
  final num section;

  ///
  /// Constructor
  ///
  const CreatePlayerRequest({
    required this.playerName,
    required this.password,
    required this.crew,
    required this.major,
    required this.section,
  });

  ///
  /// Get properties of object for comparison
  ///
  @override
  List<Object?> get props => [
        playerName,
        password,
        crew,
        major,
        section,
      ];

  Map<String, dynamic> toJson() {
    return {
      'playerName': playerName,
      'password': password,
      'crew': crew.toString(),
      'major': major.toString(),
      'section': section.toString()
    };
  }

  @override
  String toString() {
    return 'CreatePlayerRequest(playerName: $playerName, '
        'password: $password, crew: $crew, major: $major, section: $section)';
  }

  ///
  /// Convert object to JSON.
  ///
  JSON get asJson => {
        'playerName': playerName,
        'password': password,
        'crew': crew.toString(),
        'major': major.toString(),
        'section': section.toString()
      };
}
