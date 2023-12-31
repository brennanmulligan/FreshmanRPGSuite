import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:game_manager/pages/dashboard/widgets/navigation_card.dart';
import 'package:game_manager/pages/create_player/create_player_page.dart';
import 'package:game_manager/pages/change_password/change_password_page.dart';
import 'package:game_manager/pages/quest/create_edit_quest_page.dart';
import 'package:game_manager/repository/player/player_repository.dart';


class DashboardView extends StatelessWidget {
  const DashboardView({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Dashboard'),
      ),
      body: SafeArea(
        minimum: const EdgeInsets.all(12),
        child: ListView(
          shrinkWrap: true,
          children: [
            const NavigationCard(
              cardTitle: 'Create Player',
              cardIcon: Icon(Icons.accessibility_new_rounded),
              cardLink: CreatePlayerPage(),
            ),
            NavigationCard(
              cardTitle: 'Change Player Password',
              cardIcon: const Icon(Icons.lock_open),
              cardLink: ChangePasswordPage(playerRepository: PlayerRepository(dio: Dio())),
            ),
            const NavigationCard(
              cardTitle: 'Create/Edit a Quest',
              cardIcon: Icon(Icons.assignment_add),
              cardLink: CreateEditQuestPage(),
            ),
          ],
        ),
      ),
    );
  }
}
