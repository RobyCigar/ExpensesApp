package com.example.roomwordapp.ui.settings

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.roomwordapp.databinding.FragmentSettingsBinding
import com.google.android.gms.wallet.PaymentsClient
import com.google.android.gms.wallet.Wallet
import com.google.android.gms.wallet.WalletConstants
import com.google.android.gms.wallet.button.ButtonOptions
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


class NotificationsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var paymentsClient: PaymentsClient
    private val baseCardPaymentMethod = JSONObject().apply {
        put("type", "CARD")
        put("parameters", JSONObject().apply {
            put("allowedCardNetworks", JSONArray(listOf("VISA", "MASTERCARD")))
            put("allowedAuthMethods", JSONArray(listOf("PAN_ONLY", "CRYPTOGRAM_3DS")))
        })
    }

    private val googlePayBaseConfiguration = JSONObject().apply {
        put("apiVersion", 2)
        put("apiVersionMinor", 0)
        put("allowedPaymentMethods",  JSONArray().put(baseCardPaymentMethod))
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val settingsViewModel =
            ViewModelProvider(this)[SettingsViewModel::class.java]

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textNotifications
        settingsViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
        }

        // The Google Pay button is a layout file – take the root view
        // The Google Pay button is a layout file – take the root view
        val googlePayButton = binding.googlePayPaymentButton
        try {
            googlePayButton.initialize(
                ButtonOptions.newBuilder()
                    .setAllowedPaymentMethods(baseCardPaymentMethod().toString())
                    .build()
            )
            googlePayButton.setOnClickListener(this::requestPayment)
        } catch (e: JSONException) {
            // Keep Google Pay button hidden (consider logging this to your app analytics service)
        }

        // Setup buttons
        googlePayButton.initialize(
            ButtonOptions.newBuilder()
                .setAllowedPaymentMethods(allowedCardAuthMethods.toString()).build()
        )
        googlePayButton.setOnClickListener { requestPayment(view= requireView()) }

        // Check Google Pay availability
        val activity = requireActivity()
        paymentsClient = createPaymentsClient(activity)

        return root
    }

    private val allowedCardNetworks = JSONArray(listOf(
        "AMEX",
        "DISCOVER",
        "INTERAC",
        "JCB",
        "MASTERCARD",
        "VISA"))

    private val allowedCardAuthMethods = JSONArray(listOf(
        "PAN_ONLY",
        "CRYPTOGRAM_3DS"))
    private fun baseCardPaymentMethod(): JSONObject {
        return JSONObject().apply {

            val parameters = JSONObject().apply {
                put("allowedAuthMethods", allowedCardAuthMethods)
                put("allowedCardNetworks", allowedCardNetworks)
                put("billingAddressRequired", true)
                put("billingAddressParameters", JSONObject().apply {
                    put("format", "FULL")
                })
            }

            put("type", "CARD")
            put("parameters", parameters)
        }
    }
    private fun cardPaymentMethod(): JSONObject {
        val cardPaymentMethod = baseCardPaymentMethod()
        cardPaymentMethod.put("tokenizationSpecification", gatewayTokenizationSpecification())

        return cardPaymentMethod
    }
    private fun gatewayTokenizationSpecification(): JSONObject {
        return JSONObject().apply {
            put("type", "PAYMENT_GATEWAY")
            put("parameters", JSONObject(mapOf(
                "gateway" to "example",
                "gatewayMerchantId" to "exampleGatewayMerchantId")))
        }
    }


    private fun requestPayment(view: View?) {
        val googlePayButton = binding.googlePayPaymentButton

        // Disables the button to prevent multiple clicks.
        googlePayButton.isClickable = false

        // The price provided to the API should include taxes and shipping.
        // This price is not displayed to the user.
        val dummyPriceCents: Long = 100
        val shippingCostCents: Long = 900
        val totalPriceCents = dummyPriceCents + shippingCostCents

    }
    private fun createPaymentsClient(activity: Activity): PaymentsClient {
        val walletOptions = Wallet.WalletOptions.Builder()
            .setEnvironment(WalletConstants.ENVIRONMENT_TEST).build()
        return Wallet.getPaymentsClient(activity, walletOptions)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class PaymentsUtil {

}
