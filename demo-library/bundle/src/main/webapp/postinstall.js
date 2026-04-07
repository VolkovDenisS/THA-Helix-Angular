const { spawnSync } = require('child_process');
spawnSync('npx schematics', ['@helix/schematics:workspace'], { stdio: 'inherit', shell: true, encoding: 'utf-8' });