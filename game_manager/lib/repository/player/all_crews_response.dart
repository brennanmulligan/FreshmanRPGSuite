import 'package:equatable/equatable.dart';

import '../../type_definitions.dart';
import 'crew.dart';

class AllCrewsResponse extends Equatable {
  final bool success;
  final List<Crew> crews;

  const AllCrewsResponse(this.success, {required this.crews});

  const AllCrewsResponse.allFields({required this.success, required this.crews});

  factory AllCrewsResponse.fromJson({
    required JSON json,
  }) {
    return AllCrewsResponse.allFields(
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
    return 'AllCrewsResponse(crews: $crews)';
  }
}