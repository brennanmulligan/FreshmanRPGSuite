import 'package:equatable/equatable.dart';
import '../../type_definitions.dart';

///
/// Object to response for completing login.
///
class GeneralResponse extends Equatable {
  final bool success;
  final String description;


  ///
  /// Constructor
  ///
  const GeneralResponse({
    required this.success,
    required this.description
  });

  factory GeneralResponse.fromJson({
    required JSON json,
  }) {
    return GeneralResponse(
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

  ///
  /// Convert object to JSON.
  ///
  Map<String, dynamic> toJson() {
    return {'success': success, 'description': description};
  }

  @override
  String toString() {
    return 'GeneralResponse(success: $success, description: $description)';
  }
}
