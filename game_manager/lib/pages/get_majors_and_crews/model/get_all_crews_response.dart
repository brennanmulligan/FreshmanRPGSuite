import 'package:equatable/equatable.dart';

import '../../../type_definitions.dart';

class GetAllCrewsResponse extends Equatable{
  final List<String> crews;
  final bool success;
  final String description;

  /**
   * Creates a new response for getting all crews. Requires a list of crews,
   * whether the request was completed successfully and a description
   * of what the response holds
   */
  const GetAllCrewsResponse({
    required this.crews,
    required this.success,
    required this.description
  });

  /**
   * Converts a response from Json to a response Object
   */
  factory GetAllCrewsResponse.fromJson({
    required JSON json,
  }) {
    return GetAllCrewsResponse(
        crews: json['crews'],
        success: json['success'],
        description: json['description']
    );
  }

  /**
   * Gets a list of the values this object holds
   */
  List<Object?> get props => [
    success, description, crews
  ];

  /**
   * Converts the current response into a String
   */
  @override
  String toString(){
    return 'GetAllCrewsResponse(success: $success, description: $description)';
  }
}