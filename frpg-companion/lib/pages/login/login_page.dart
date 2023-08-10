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
  final formKey = GlobalKey<FormState>();
  final playerName = TextEditingController();
  final password = TextEditingController();

  @override
  void initState() {
    super.initState();
  }

  @override
  dispose() {
    super.dispose();
    playerName.dispose();
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
                            margin: EdgeInsets.only(
                              bottom: MediaQuery.of(context).size.height - 450
                            ),
                            padding: EdgeInsets.zero,
                        ),
                      );
                    }},
                    builder: (context, state) {
                      if (state is LoginComplete) {
                        return buildRequestCompleteScreen(state.response);
                      } else {
                        return buildInputScreen(context);
                      }
                    }))));
  }

  void submitForm(BuildContext context) {
    if (formKey.currentState!.validate()) {
      BlocProvider.of<LoginBloc>(context).add(SendLoginEvent(
        playerName.text,
        password.text,
      ));
    }
  }

  Widget buildInputScreen(BuildContext context) => Padding(
        padding: const EdgeInsets.all(24.0),
        child: Center(
          child: Column(
            children: [

              Form(
                key: formKey,
                child: Column(
                  children: [

                    TextFormField(
                      textInputAction: TextInputAction.next,
                      validator: (fieldText) {
                        if (fieldText == null || fieldText.isEmpty) {
                          return "Username field is empty";
                        }
                        return null;
                      },
                      controller: playerName,
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

                    TextFormField(
                      onFieldSubmitted: (fieldText) {
                        submitForm(context);
                      },
                      validator: (fieldText) {
                        if (fieldText == null || fieldText.isEmpty) {
                          return "Password field is empty";
                        }
                        return null;
                      },
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

                    SubmitButtonBuilder(
                        playerName: playerName,
                        password: password,
                        loginPageState: this),
                  ],
                ),
              ),
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
    required this.playerName,
    required this.password,
    required this.loginPageState,
  }) : super(key: key);

  final TextEditingController playerName;
  final TextEditingController password;
  final _LoginPageState loginPageState;

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
        onPressed: () {
          loginPageState.submitForm(context);
        },
        child: const Text(
          "Login",
          style: TextStyle(color: Colors.black),
        ),
      ),
    );
  }
}
