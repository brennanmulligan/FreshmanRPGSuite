import 'package:equatable/equatable.dart';
import '../../type_definitions.dart';

class Major extends Equatable {
  final int id;
  final String name;

  const Major(
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

  factory Major.fromJson({
    required JSON json,
  }) {
    return Major(
        id: json['id'],
        name: json['name']
    );
  }
}

