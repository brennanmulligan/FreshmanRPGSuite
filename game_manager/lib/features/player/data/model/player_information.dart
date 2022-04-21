import 'package:equatable/equatable.dart';
import 'package:frpg_networking_api/networking/service_client/type_definitions/type_definitions.dart';

class PlayerInformation extends Equatable {
  final String name;
	final String password;
	final num crew;
	final num major;
	final num section;
  
  ///
  /// Constructor
  ///
  const PlayerInformation({
    required this.name,
    required this.password,
    required this.crew,
    required this.major,
    required this.section,
  });

  ///
  /// Factory mapping 'JSON' to 'PlayerInformation'
  ///
  factory PlayerInformation.fromJson({
    required JSON json,
  }) {
    return PlayerInformation(
      name: json['name'],
      password: json['password'],
      crew: json['crew'],
      major: json['major'],
      section: json['section'],
    );
  }

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
    return 'PlayerInformation(name: $name, '
      'password: $password, crew: $crew, major: $major, section: $section)';
  }
}