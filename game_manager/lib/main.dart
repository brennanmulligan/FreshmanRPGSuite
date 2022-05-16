import 'package:flutter/material.dart';
import 'package:game_manager/features/dashboard/presentaion/dashboard_view.dart';
import 'package:hooks_riverpod/hooks_riverpod.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:game_manager/features/qr/presentation/create_qr_code_view.dart';
import 'features/network/network_provider.dart';
import 'features/player/presentation/create_player_view.dart';

import 'features/network/network_provider.dart';

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

void main() async {
  final container = ProviderContainer();
  await initialize(
    container: container,
  );

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
        title: 'Freshman RPG Game Manager',
        home: const DashboardView(),
      ),
    ),
  );
}

///
///
///
Future<void> initialize({
  required ProviderContainer container,
}) async {
  ///
  /// Load app configurations, then tell all
  /// providers dependent on this data to
  /// refresh.
  ///
  WidgetsFlutterBinding.ensureInitialized();
  await dotenv.load(fileName: '.env');
  container.read(NetworkProvider.networkController.notifier).setBaseURL();
  container.refresh(NetworkProvider.serviceClient);
}
