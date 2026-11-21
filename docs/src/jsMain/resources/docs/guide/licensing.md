---
sidebar_position: 3
---

# Licensing

## The wrapper

`ktjs-xterm` is MIT licensed. See [LICENSE](https://github.com/shijianjs/ktjs-xterm/blob/main/LICENSE).

## Upstream

`@xterm/xterm` and every addon wrapped here are MIT licensed:

- `@xterm/xterm` 6.0.0 — MIT, © The xterm.js authors
- `@xterm/addon-fit` 0.11.0, `@xterm/addon-search` 0.16.0, `@xterm/addon-serialize` 0.14.0,
  `@xterm/addon-unicode11` 0.9.0, `@xterm/addon-web-links` 0.12.0 — MIT, same holder

Their terms travel with their own packages, which remain dependencies of this module; nothing here
re-licenses them.

## What a consumer must do

| Question | Answer |
| --- | --- |
| May I ship this in a closed-source product? | Yes — MIT permits it. Keep the upstream copyright notice, which the bundled npm packages carry. |
| Must I publish my source? | No. |
| Are the addons a separate licence? | Only nominally: each is MIT too. No wrapped part has a commercial edition. |

The upstream repository also publishes `@xterm/headless` and website tooling that this wrapper does not
touch; their licences are not part of this module.
