import 'package:equatable/equatable.dart';
import '../../type_definitions.dart';

class Crew extends Equatable {
  final int id;
  final String name;

  const Crew(
      {required this.id,
        required this.name});

  @override
  List<Object?> get props => [id, name];

  ///
  /// Convert object to JSON.
  ///
  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'name': name
    };
  }

  factory Crew.fromJson({
    required JSON json,
  }) {
    return Crew(
        id: json['id'],
        name: json['name']
    );
  }
}

