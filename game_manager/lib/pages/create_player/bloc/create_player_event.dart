part of 'create_player_bloc.dart';

@immutable
abstract class CreatePlayerPageEvent {}

class SendCreatePlayerEvent extends CreatePlayerPageEvent {
  final String name;
  final String password;
  final num crew;
  final num major;
  final num section;

  SendCreatePlayerEvent(this.name, this.password, this.crew, this.major, this
      .section);

  @override
  String toString() {
    return 'SendCreatePlayerEvent(name: $name, '
        'password: $password, crew: $crew, major: $major, section: $section)';
  }
}

class SendGetMajorsAndCrewsEvent extends CreatePlayerPageEvent {

  SendGetMajorsAndCrewsEvent();
/*
returns the major and crews event string
 */
  @override
  String toString() {
    return 'SendGetMajorsAndCrewsEvent()';
  }
}

