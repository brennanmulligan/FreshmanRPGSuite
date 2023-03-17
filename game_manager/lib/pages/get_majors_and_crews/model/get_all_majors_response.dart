import 'package:equatable/equatable.dart';

import '../../../type_definitions.dart';

class GetAllMajorsResponse extends Equatable{
  final List<String> majors;
  final bool success;
  final String description;

  /**
   * Creates a new response for getting all crews. Requires a list of crews,
   * whether the request was completed successfully and a description
   * of what the response holds
   */
  const GetAllMajorsResponse({
    required this.majors,
    required this.success,
    required this.description
  });


  /**
   * Converts a response from Json to a response Object
   */
  factory GetAllMajorsResponse.fromJson({
    required JSON json,
  }) {
    return GetAllMajorsResponse(
        majors: json['majors'],
        success: json['success'],
        description: json['description']
    );
  }


  /**
   * Gets a list of the values this object holds
   */
  List<Object?> get props => [
    success, description, majors
  ];


  /**
   * Converts the current response into a String
   */
  @override
  String toString(){
    return 'GetAllMajorsResponse(success: $success, description: $description)';
  }
}