import 'package:equatable/equatable.dart';
import 'package:frpg_networking_api/networking/service_client/type_definitions/type_definitions.dart';

///
/// Object to request creating a player.
///
class CreatePlayerRequest extends Equatable {
  final String name;
  final String password;
  final num crew;
  final num major;
  final num section;

  ///
  /// Constructor
  ///
  const CreatePlayerRequest({
    required this.name,
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
        name,
        password,
        crew,
        major,
        section,
      ];

  @override
  String toString() {
    return 'CreatePlayerRequest(name: $name, '
        'password: $password, crew: $crew, major: $major, section: $section)';
  }

  ///
  /// Convert object to JSON.
  ///
  JSON get asJson => {
        'name': name,
        'password': password,
        'crew': crew,
        'major': major,
        'section': section
      };
}
