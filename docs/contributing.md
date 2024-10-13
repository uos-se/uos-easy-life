# Contributing

## About the Development Environment

The project uses a development container to provide a consistent development environment. The development container includes various configurations for convenience. One of them is the shortcut script, which allows you to run the script directly from the terminal by adding a script to the `scripts` folder and granting execution permission.

The following scripts are currently included:

- `unlock`: Unlocks the secrets.
- `dev`: Starts the development server.
- `deploy`: Deploys the project.
- `build`: Builds the project.

## Configuring Development Environment

#### Prerequisites

- `x86-64` or `AMD64` architecture
- `Docker` or equivalent container runtime
- `Visual Studio Code`
- `Remote - Containers extension` for VS Code

#### Setup

1. `git clone https://github.com/username/uos-easy-life.git`
1. Open the project in VS Code
1. Reopen the project in a container (`Ctrl+Shift+P` -> `Reopen in Container`)
1. Run `unlock` to unlock the secrets

**NOTE**: The unlock token is required to unlock the secrets. Contact the project manager to get the unlock token.

#### Local Development Environment

1. Run `dev`
1. Open `http://localhost:5173` in your browser

## Deployment

1. Run `deploy`

## Contribution Guidelines

#### How to Contribute

1. Create a new branch (`git checkout -b feature/feature-name`)
1. Commit your changes (`git commit -am 'Add new feature'`)
1. Push to the branch (`git push origin feature/feature-name`)
1. Create a new Pull Request

Refer to the `Branch Naming Convention` section for more details about branch naming.

- **NOTE**: Be sure to rebase your branch on the latest `main` branch before creating a new Pull Request.
- **NOTE**: Never push directly to the `main` branch.

#### Branch Naming Convention

- `feature/feature-name`: For new features
- `bugfix/bug-name`: For bug fixes
- `hotfix/hotfix-name`: For critical bug fixes
- `docs/document-name`: For documentation changes

#### Commit Message Convention

- Use the following format for commit messages:
  ```
  If this commit is applied, it will <commit message>
  ```
- Unless it is very minor and obvious, always include a description of what the commit does.
- Good dxamples:
  - `Fix session reset issue`
  - `Add contribution guidelines document`
- Bad examples:
  - `Fix`: This does not tell us anything about what the commit does
  - `Login implementation`: This does not follows the format

## Secret Management

The project uses `git-crypt` to manage secrets. All files starting with `secret-` prefix are encrypted. To unlock the secrets, run the `unlock` script.
