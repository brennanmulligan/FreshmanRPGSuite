part of 'create_many_players_bloc.dart';

@immutable
abstract class CreateManyPlayersEvent {}

class SendCreateManyPlayersEvent extends CreateManyPlayersEvent {
  final io.File csvFile;

  SendCreateManyPlayersEvent(this.csvFile);

  @override
  String toString() {
    return 'SendCreateManyPlayersEvent(csvFile: $csvFile)';
  }
}


