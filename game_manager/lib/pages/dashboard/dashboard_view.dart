import 'package:flutter/material.dart';
import 'package:game_manager/pages/dashboard/widgets/navigation_card.dart';
import 'package:game_manager/pages/create_player/create_player_page.dart';

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
          children: const [

            NavigationCard(
              cardTitle: 'Create Player',
              cardIcon: Icon(Icons.accessibility_new_rounded),
              cardLink: CreatePlayerPage(),
            ),
          ],

          shrinkWrap: true,
        ),
      ),
    );
  }
}
