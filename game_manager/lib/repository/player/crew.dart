import 'package:equatable/equatable.dart';
import '../../type_definitions.dart';

class Crew extends Equatable {
  final int crewID;
  final String name;

  const Crew(
      {required this.crewID,
        required this.name});

  @override
  List<Object?> get props => [crewID, name];

  ///
  /// Convert object to JSON.
  ///
  Map<String, dynamic> toJson() {
    return {
      'crewID': crewID,
      'name': name
    };
  }

  factory Crew.fromJson({
    required JSON json,
  }) {
    return Crew(
        crewID: json['crewID'],
        name: json['name']
    );
  }
}

