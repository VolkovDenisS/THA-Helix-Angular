const { spawnSync } = require('child_process');
spawnSync('npx schematics', ['@helix/schematics:generate-manifest'], { stdio: 'inherit', shell: true, encoding: 'utf-8' });