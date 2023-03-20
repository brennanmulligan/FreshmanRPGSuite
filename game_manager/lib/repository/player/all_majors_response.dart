import 'package:equatable/equatable.dart';

import '../../type_definitions.dart';
import 'major.dart';

class AllMajorsResponse extends Equatable {
  final bool success;
  final List<Major> majors;

  const AllMajorsResponse(this.success, {required this.majors});

  const AllMajorsResponse.allFields({required this.success, required this.majors});

  factory AllMajorsResponse.fromJson({
    required JSON json,
  }) {
    return AllMajorsResponse.allFields(
      success: json['success'],
      majors: (json['majors'] as List)
          .map((e) => Major.fromJson(json: e))
          .toList()
    );
  }

  @override
  List<Object> get props => [success, majors];

  @override
  String toString() {
    return 'AllMajorsResponse(majors: $majors)';
  }
}