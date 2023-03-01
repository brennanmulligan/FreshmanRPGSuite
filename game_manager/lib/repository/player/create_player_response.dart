import 'package:equatable/equatable.dart';

import '../../type_definitions.dart';

///
/// Object to request creating a player.
///
class CreatePlayerResponse extends Equatable {
  final bool success;
  final String description;

  ///
  /// Constructor
  ///
  const CreatePlayerResponse({
    required this.success,
    required this.description
  });

  factory CreatePlayerResponse.fromJson({
    required JSON json,
  }) {
     return CreatePlayerResponse(
       success: json['success'],
       description: json['description']
    );
  }

  ///
  /// Get properties of object for comparison
  ///
  @override
  List<Object?> get props => [
    success, description
  ];
  
  @override
  String toString() {
    return 'CreatePlayerResponse(success: $success, description: $description)';
  }
}