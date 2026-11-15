using System.Threading;
using System.Threading.Tasks;
namespace Tetris3D.Account {
    public sealed class AccountResult {
        public bool Success { get; }
        public string Message { get; }
        public AccountResult(bool success, string message) { Success = success; Message = message; }
    }
    // Passwords and verification codes must never be persisted in PlayerPrefs or logs.
    public interface IAccountService {
        bool IsConfigured { get; }
        Task<AccountResult> LoginAsync(string email, string password, CancellationToken cancellationToken);
        Task<AccountResult> SendRegistrationCodeAsync(string email, CancellationToken cancellationToken);
        Task<AccountResult> RegisterAsync(string email, string code, string password, CancellationToken cancellationToken);
    }
    public sealed class UnconfiguredAccountService : IAccountService {
        public bool IsConfigured => false;
        static Task<AccountResult> Unavailable(CancellationToken token) {
            token.ThrowIfCancellationRequested();
            return Task.FromResult(new AccountResult(false, "账号服务暂未开放，请先以访客身份游玩。"));
        }
        public Task<AccountResult> LoginAsync(string email, string password, CancellationToken token) => Unavailable(token);
        public Task<AccountResult> SendRegistrationCodeAsync(string email, CancellationToken token) => Unavailable(token);
        public Task<AccountResult> RegisterAsync(string email, string code, string password, CancellationToken token) => Unavailable(token);
    }
}
