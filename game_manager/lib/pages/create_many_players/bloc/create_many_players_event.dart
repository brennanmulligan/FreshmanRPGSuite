part of 'create_many_players_bloc.dart';

@immutable
abstract class CreateManyPlayersEvent {}

class SendCreateManyPlayersEvent extends CreateManyPlayersEvent {
  final List<String> fileLines;

  SendCreateManyPlayersEvent(this.fileLines);

  @override
  String toString() {
    return 'SendCreateManyPlayersEvent(csvFile: $fileLines)';
  }
}


