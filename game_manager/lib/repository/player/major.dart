import 'package:equatable/equatable.dart';
import '../../type_definitions.dart';

class Major extends Equatable {
  final int majorID;
  final String name;

  const Major(
      {required this.majorID,
        required this.name});

  @override
  List<Object?> get props => [majorID, name];

  ///
  /// Convert object to JSON.
  ///
  Map<String, dynamic> toJson() {
    return {
      'majorID': majorID,
      'name': name
    };
  }

  factory Major.fromJson({
    required JSON json,
  }) {
    return Major(
        majorID: json['majorID'],
        name: json['name']
    );
  }
}

