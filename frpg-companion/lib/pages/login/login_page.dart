import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

import '../../repository/login_repository/login_repository.dart';
import '../shared_widgets/notification_card.dart';
import 'bloc/login_bloc.dart';
import '../../repository/login_repository/login_with_credentials_response.dart';

class LoginPage extends StatefulWidget {
  const LoginPage({Key? key}) : super(key: key);

  @override
  State<LoginPage> createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  final username = TextEditingController();
  final password = TextEditingController();

  @override
  void initState() {
    super.initState();
  }

  @override
  dispose() {
    super.dispose();
    username.dispose();
    password.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        resizeToAvoidBottomInset: false,
        appBar: AppBar(
          title: const Text('Login'),
        ),
        body: RepositoryProvider(
            create: (context) => LoginRepository(),
            child: BlocProvider<LoginBloc>(
                create: (context) => LoginBloc(
                    context: context,
                    loginRepository: context.read<LoginRepository>()),
                child: BlocConsumer<LoginBloc, LoginState>(
                    listener: (context, state) {if (state is LoginFailed) {
                      ScaffoldMessenger.of(context).showSnackBar(
                        SnackBar(
                            content: NotificationCard(
                                cardTitle: "Login Failed",
                                description: "Please try again",
                                success: state.response.success
                            ),
                            duration: const Duration(seconds: 3),
                            behavior: SnackBarBehavior.floating,
                            padding: EdgeInsets.zero
                        ),
                      );
                    }},
                    builder: (context, state) {
                      if (state is LoginComplete) {
                        return buildRequestCompleteScreen(state.response);
                      } else {
                        return buildInputScreen();
                      }
                    }))));
  }

  Widget buildInputScreen() => Padding(
        padding: const EdgeInsets.all(24.0),
        child: Center(
          child: Column(
            children: [
              TextField(
                controller: username,
                decoration: InputDecoration(
                  label: Row(
                    children: const [
                      Icon(Icons.person),
                      SizedBox(
                        width: 10,
                      ),
                      Text('Username'),
                    ],
                  ),
                  fillColor: Colors.grey,
                ),
              ),
              TextField(
                obscureText: true,
                controller: password,
                decoration: InputDecoration(
                  label: Row(
                    children: const [
                      Icon(Icons.key),
                      SizedBox(
                        width: 10,
                      ),
                      Text('Password'),
                    ],
                  ),
                  fillColor: Colors.grey,
                ),
              ),
              const SizedBox(
                height: 60,
              ),
              SubmitButtonBuilder(username: username, password: password)
            ],
          ),
        ),
      );

  Widget buildRequestCompleteScreen(LoginWithCredentialsResponse data) =>
      Center(child: Text('$data'));
}

class SubmitButtonBuilder extends StatelessWidget {
  const SubmitButtonBuilder({
    Key? key,
    required this.username,
    required this.password,
  }) : super(key: key);

  final TextEditingController username;
  final TextEditingController password;

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.all(1),
      child: ElevatedButton(
        style: ElevatedButton.styleFrom(
          foregroundColor: Colors.blue,
          minimumSize: const Size(double.infinity, 50),
          shape: const RoundedRectangleBorder(
            borderRadius: BorderRadius.all(Radius.circular(1)),
          ),
        ),
        onPressed: () => BlocProvider.of<LoginBloc>(context).add(SendLoginEvent(
          username.text,
          password.text,
        )),
        child: const Text(
          "Login",
          style: TextStyle(color: Colors.black),
        ),
      ),
    );
  }
}
