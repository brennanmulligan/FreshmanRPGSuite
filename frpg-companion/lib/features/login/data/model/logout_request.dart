import 'package:equatable/equatable.dart';

class LogoutRequest extends Equatable {
  final num playerID;

  const LogoutRequest({required this.playerID});

  @override
  List<Object?> get props => [playerID];
}
