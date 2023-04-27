import 'package:equatable/equatable.dart';

class ObjectiveCompletionTypeDTO extends Equatable {
  final int id;
  final String name;

  const ObjectiveCompletionTypeDTO({required this.id, required this.name});

  @override
  // TODO: implement props
  List<Object?> get props => [id, name];

}