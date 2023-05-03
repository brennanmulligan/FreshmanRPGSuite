import 'package:equatable/equatable.dart';

import '../../type_definitions.dart';
import 'crew.dart';

class GetAllCrewsResponse extends Equatable {
  final bool success;
  final List<Crew> crews;

  const GetAllCrewsResponse(this.success, {required this.crews});

  const GetAllCrewsResponse.allFields({required this.success, required this.crews});

  factory GetAllCrewsResponse.fromJson({
    required JSON json,
  }) {
    return GetAllCrewsResponse.allFields(
        success: json['success'],
        crews: (json['crews'] as List)
            .map((e) => Crew.fromJson(json: e))
            .toList()
    );
  }

  @override
  List<Object> get props => [success, crews];

  @override
  String toString() {
    return 'GetAllCrewsResponse(crews: $crews)';
  }
}