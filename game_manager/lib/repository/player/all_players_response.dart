import 'package:equatable/equatable.dart';

class AllPlayersResponse extends Equatable {
  final List<String> players = ["Player 1", "Player 2", "Player 3"];

  @override
  List<Object?> get props => [players];

}