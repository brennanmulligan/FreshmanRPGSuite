part of 'login_bloc.dart';

@immutable
abstract class LoginEvent {}

class SendLoginEvent extends LoginEvent {
  final String username;
  final String password;

  SendLoginEvent(this.username, this.password);

  @override
  String toString() {
    return 'SendLoginEvent(username: $username, password $password)';
  }
}
