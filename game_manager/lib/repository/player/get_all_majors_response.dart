import 'package:equatable/equatable.dart';

import '../../type_definitions.dart';
import 'major.dart';

class GetAllMajorsResponse extends Equatable {
  final bool success;
  final List<Major> majors;

  const GetAllMajorsResponse(this.success, {required this.majors});

  const GetAllMajorsResponse.allFields({required this.success, required this.majors});

  factory GetAllMajorsResponse.fromJson({
    required JSON json,
  }) {
    return GetAllMajorsResponse.allFields(
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
    return 'GetAllMajorsResponse(majors: $majors)';
  }
}