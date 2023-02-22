part of 'create_player_bloc.dart';

@immutable
abstract class CreatePlayerEvent {}

class SendCreatePlayerEvent extends CreatePlayerEvent {
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

