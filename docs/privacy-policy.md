# PinWear Privacy Policy

## 1. Basic Information

PinWear is a third-party Wear OS client based on the official Pinterest API. This application aims to provide a native Pinterest browsing experience for smartwatch users.

**Please Note**:
- PinWear is an independent personal project developed and maintained by individual developer He, and is not a corporate product.
- This application has no affiliation, sponsorship, partnership, or official endorsement relationship with Pinterest, Inc.
- **AI-Assisted Development Disclaimer**: During the development of this application, the developer used Artificial Intelligence (AI) tools to assist with code writing, documentation generation, and architecture discussions. All final code merges, application releases, and subsequent maintenance responsibilities are borne by the developer personally. As a development aid, the AI tools will absolutely not contact, access, or process any user's real Pinterest data.

## 2. Data Access

After your explicit authorization and login, PinWear may access the following data of yours through the official Pinterest API:
- Pinterest user account information
- Your personal Boards
- Your Pins
- Additional information related to the above

**Our Commitments**:
- **Secure Authentication**: This application uses the standard OAuth 2.0 protocol for secure authentication. PinWear will not obtain, request, or store your Pinterest account password.
- **Scope Limitation**: We only access data within the Scope explicitly authorized by you.
- **Legitimate Channels**: We strictly use the public APIs officially provided by Pinterest for data interaction and do not use any private or undocumented APIs.
- **No Violations**: We do not perform any form of data scraping or reverse engineering on the Pinterest platform.

## 3. Purpose of Data Use

PinWear accesses your data solely to implement basic browsing and display functions on your Wear OS device. The specific use cases are strictly limited to the following MVP (Minimum Viable Product) goals:
- Displaying your Pinterest content on Wear OS
- Browsing your Boards
- Browsing the Pins within your Boards
- Viewing specific Pin details

We will not use your data for any other purposes beyond the above scenarios, nor will we use your information for personalized advertising or cross-site tracking.

## 4. Data Storage

To protect the security and privacy of your data, PinWear follows minimalist and secure principles in its architecture and implementation:
- **Local Token Storage**: Your Access Token and Refresh Token are both encrypted and stored locally on the device you are currently using.
- **Key Protection**: We use the device's underlying Android Keystore system to securely generate and protect encryption keys.
- **No Server Storage**: PinWear is a pure client application. It may use temporary caches to optimize your runtime experience. We do not store Pinterest content long-term, nor do we build an offline Pinterest content library. All business data will never be uploaded or stored on our servers or any third-party cloud servers.
- **No Data Trading**: We absolutely do not sell, rent, or share any of your data.

## 5. Pinterest Content

The copyright and ownership of all Pinterest content (including images, descriptions, links, etc.) presented through PinWear belong to Pinterest or the original content creators and users.
- We strictly comply with all relevant provisions of the [Pinterest Developer Guidelines](https://developers.pinterest.com/docs/getting-started/developer-guidelines/).
- According to Pinterest's requirements, this application will provide native source link redirection in the interface so that users can open it in the official channel.

## 6. Data Deletion and Revocation

You always retain full control over your data:
- **In-App Logout**: You can execute the "Logout" operation within the PinWear application, which will clear the relevant authentication information (Tokens, etc.) saved locally on the device.
- **App Uninstallation**: By uninstalling this application from your Wear OS device, the system will automatically delete the application's local stored data, including your authentication information.
- **Platform Revocation**: You can log into the official Pinterest website or app at any time, go to "Settings" -> "Connected Apps", find PinWear, and revoke its authorization. After revocation, the application will no longer be able to access any of your new data.

## 7. Contact Information

If you have any questions about this privacy policy or need further assistance, please contact the developer via the following method:

**Email**: [dukego896@gmail.com](mailto:dukego896@gmail.com)
