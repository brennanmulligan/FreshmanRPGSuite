import 'package:flutter/material.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:frpg_companion/features/login/presentation/view/login_view.dart';
import 'package:frpg_companion/features/network/network.dart';

///
/// Data for light theme.
///
ThemeData get lightTheme => ThemeData(
      primarySwatch: Colors.blue,
      brightness: Brightness.light,
    );

///
/// Data for dark theme.
///
ThemeData get darkTheme => ThemeData(
      primarySwatch: Colors.lightBlue,
      brightness: Brightness.dark,
    );

///
/// Main runnable of the app.
///
void main() async {
  ///
  /// Load app configurations, then tell all
  /// providers dependent on this data to
  /// refresh.
  ///
  await dotenv.load(fileName: '.env');
  final container = ProviderContainer();
  container.read(NetworkProvider.networkController.notifier).assignBaseURL();
  container.refresh(NetworkProvider.serviceClient);

  ///
  /// Run the app.
  ///
  runApp(
    UncontrolledProviderScope(
      container: container,
      child: MaterialApp(
        theme: lightTheme,
        darkTheme: darkTheme,
        themeMode: ThemeMode.system,
        debugShowCheckedModeBanner: false,
        title: 'Freshman RPG Companion App',
        home: const LoginView(),
      ),
    ),
  );
}
